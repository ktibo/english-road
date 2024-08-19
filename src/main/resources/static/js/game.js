const urlParams = new URLSearchParams(window.location.search);
const levelId = urlParams.get('level');
const apiUrl = `/api/questions?level=${levelId}`;
let questions, question;
const wordElement = document.getElementById('word');
const transcriptionElement = document.getElementById('transcription');
const optionsContainer = document.getElementById('options-container');
const heartContainer = document.getElementById('heart-container');
const buttonTemplate = document.getElementById('button-template');
const templateContent = document.getElementById('template').content;
const heartTemplate = templateContent.getElementById('heart-template');
const brokenHeartTemplate = templateContent.getElementById('broken-heart-template');
const counter = document.getElementById('counter');
let currentIndex = 0;
let attempts = 3;
const hearts = [];

const updateButtonStyles = (button, addClass, removeClass) => {
    button.classList.add(addClass);
    button.classList.remove(removeClass);
};

const updateHeartsDisplay = () => {
    hearts.forEach((heart, index) => {
        heart.src = index < 3 - attempts ? brokenHeartTemplate.src : heartTemplate.src;
    });
};

const chooseOption = (isCorrect, button) => {
    updateButtonStyles(button, isCorrect ? 'btn-success' : 'btn-danger', 'btn-outline-primary');
    if (isCorrect) {
        if (++currentIndex < questions.length) {
            fillNextQuestion();
        } else {
            finishGame();
        }
    } else {
        if (--attempts === 0) {
            finishGame();
        }
        button.disabled = true;
        updateHeartsDisplay();
    }
};

const fillNextQuestion = () => {
    optionsContainer.innerHTML = '';
    question = questions[currentIndex];
    counter.textContent = `${currentIndex + 1}/${questions.length}`;
    wordElement.textContent = question.word.name;
    transcriptionElement.textContent = question.word.transcription;

    question.options.forEach(option => {
        const button = buttonTemplate.cloneNode(true);
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
};

const fetchQuestions = async () => {
    try {
        const response = await fetch(apiUrl);
        if (!response.ok) throw new Error('Response is not ok:(');
        return await response.json();
    } catch (error) {
        console.error('Error while getting word list:', error);
        return [];
    }
};

const loadQuestions = async () => {
    questions = await fetchQuestions();
    for (let i = 0; i < attempts; i++) {
        const heart = heartTemplate.cloneNode(true);
        heartContainer.appendChild(heart);
        hearts.push(heart);
    }
    fillNextQuestion();
};

window.onload = loadQuestions;
