const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
const tooltipList = [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl, {
    trigger: 'hover',
    container: 'body'
}));
const urlParams = new URLSearchParams(window.location.search);
const levelId = urlParams.get('level');
const apiUrlGetQuestions = `/api/questions?level=${levelId}`;
const apiUrlCompleteLevel = `/api/completeLevel/${levelId}`;
const apiUrlHints = `/api/hints`;
const apiUrlSpendHint = `/api/hints/`;
let questions, question;
const wordElement = document.getElementById('word');
const transcriptionElement = document.getElementById('transcription');
const optionsContainer = document.getElementById('options-container');
const heartContainer = document.getElementById('heart-container');
const optionTemplate = document.getElementById('options-templates')
    .content.getElementById('option-template');
const livesTemplateContent = document.getElementById('lives-templates').content;
const heartTemplate = livesTemplateContent.getElementById('heart-template');
const brokenHeartTemplate = livesTemplateContent.getElementById('broken-heart-template');
const counter = document.getElementById('counter');
let currentIndex = 0;
let attempts = 3;
const hearts = [];
let defence = false;

const updateButtonStyles = (button, addClass, removeClass) => {
    button.classList.add(addClass);
    button.classList.remove(removeClass);
};

const updateHeartsDisplay = () => {
    hearts.forEach((heart, index) => {
        heart.src = index < hearts.length - attempts ? brokenHeartTemplate.src : heartTemplate.src;
    });
};

const chooseOption = (isCorrect, button) => {
    updateButtonStyles(button, isCorrect ? 'btn-success' : 'btn-danger', 'btn-outline-primary');
    if (isCorrect) {
        if (++currentIndex < questions.length) {
            fillNextQuestion();
        } else {
            completeLevel();
            finishGame();
        }
    } else {
        if (!defence) {
            attempts--;
            if (attempts === 3) {
                spendHint(document.getElementById('button-hint3'));
            }
        }
        defence = false;
        if (attempts === 0) {
            finishGame();
        }
        button.disabled = true;
        updateHeartsDisplay();
    }
};

const fillNextQuestion = () => {
    defence = false;
    optionsContainer.innerHTML = '';
    question = questions[currentIndex];
    counter.textContent = `${currentIndex + 1}/${questions.length}`;
    wordElement.textContent = question.word.name;
    transcriptionElement.textContent = question.word.transcription;

    question.options.forEach(option => {
        const button = optionTemplate.cloneNode(true);
        button.textContent = option;
        button.style.display = 'inline-block';
        button.addEventListener('click', () => chooseOption(option === question.correctOption, button));
        optionsContainer.appendChild(button);
    });
};

const finishGame = () => {
    document.querySelectorAll('.btn').forEach(btn => {
        if (btn.textContent === question.correctOption) {
            updateButtonStyles(btn, 'btn-success', 'btn-outline-primary');
        }
        btn.disabled = true;
    });

    document.getElementById("back-to-menu").style.visibility = "visible";

};

const fetchQuestions = async () => {
    try {
        const response = await fetch(apiUrlGetQuestions);
        if (!response.ok) throw new Error('Response is not ok:(');
        return await response.json();
    } catch (error) {
        console.error('Error while getting word list:', error);
        return [];
    }
};


const completeLevel = async () => {

    await fetch(apiUrlCompleteLevel, {
        method: 'POST',
        headers: {
            [header]: token, // CSRF-токен
        }
    }).then(response => {
        if (!response.ok) {
            throw new Error('Response "completeLevel" is not ok :(')
        }
    });

    getHints();

}

function getHints() {
    return fetch(apiUrlHints, {
        method: 'GET',
        headers: {
            [header]: token, // CSRF-токен
        }
    }).then(response => {
        if (response.ok) {
            return response.text();
        }
        throw new Error('Response "get hints" was not ok :(');
    })
        .then(data => {
            const hints = +getCookie('hints');
            const binaryHints = hints.toString(2).padStart(3, '0'); // Двоичный вид
            for (let i = 0; i < binaryHints.length; i++) {
                const element = document.getElementById('hint' + (i + 1));
                let index = binaryHints.length - i - 1;
                if (binaryHints[index] === '0') {
                    activateHint(element, false);
                } else {
                    activateHint(element, true);
                    // Доп. жизнь
                    if (i === 2) {
                        attempts++;
                        element.children[0].style.pointerEvents = 'none';
                    }
                }
            }
        });
}

const loadQuestions = async () => {
    questions = await fetchQuestions();

    await getHints();

    for (let i = 0; i < attempts; i++) {
        const heart = heartTemplate.cloneNode(true);
        heartContainer.appendChild(heart);
        hearts.push(heart);
    }
    fillNextQuestion();
};

window.onload = loadQuestions;

function getCookie(name) {
    return document.cookie
        .split('; ')
        .map(cookie => cookie.split('='))
        .reduce((acc, [key, value]) => ({...acc, [key]: decodeURIComponent(value)}), {})[name] || null;
}

function spendHint(button) {

    const hintIndex = +button.id.replace('button-hint', '');

    if (hintIndex === 1) {
        removeHalfOption();
    } else if (hintIndex === 2) {
        defence = true;
    }

    fetch(apiUrlSpendHint + hintIndex, {
        method: 'POST',
        headers: {
            [header]: token, // CSRF-токен
        }
    })
        .then(response => {
            if (response.ok) {

                activateHint(button.parentElement, false);
            }
        });

}

function removeHalfOption() {

    let indexCorrect = -1;

    for (let i = 0; i < question.options.length; i++) {
        if (question.options[i] === question.correctOption) {
            indexCorrect = i;
            break;
        }
    }

    let indexOption = indexCorrect;

    while (indexOption === indexCorrect) {
        indexOption = Math.floor(Math.random() * question.options.length);
    }

    for (let i = 0; i < question.options.length; i++) {
        if (i !== indexCorrect && i !== indexOption) {
            optionsContainer.children[i].style.visibility = "hidden";
        }
    }

}

function activateHint(element, activate) {
    if (activate) {
        element.style.opacity = '1';
        element.children[0].style.pointerEvents = 'auto';
    } else {
        element.style.opacity = '0.25';
        element.children[0].style.pointerEvents = 'none';
    }
}