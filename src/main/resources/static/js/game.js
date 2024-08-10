let urlParams = new URLSearchParams(window.location.search);
let levelId = urlParams.get('level');
let apiUrl = '/api/questions?level='+levelId;
let questions;

let questionContainer = document.getElementById('question-container');
let wordElement = document.getElementById('word');
let transcriptionElement = document.getElementById('transcription');
let optionsContainer = document.getElementById('options-container');
let buttonTemplate = document.getElementById('button-template');
let currentIndex = 0;

async function loadQuestions() {

    questions = await fetchQuestions();
    fillNextQuestion();

}

function fillNextQuestion() {

    optionsContainer.innerHTML = '';

    let question = questions[currentIndex++];

    wordElement.textContent = question.word.name;
    transcriptionElement.textContent = question.word.transcription;

    question.options.forEach(option => {
        let button = buttonTemplate.cloneNode(true);
        button.textContent = option.name;
        button.style.display = 'inline-block';

        button.addEventListener('click', () => {
            if (button.textContent == question.correctOption.name) {
                fillNextQuestion();
                //console.log('ok');
            } else {
                //console.log(button.textContent);
                //console.log(question.correctOption.name);
            }
        });

        optionsContainer.appendChild(button);
    });

}

async function fetchQuestions() {
    try {
        let response = await fetch(apiUrl);
        if (!response.ok) {
            throw new Error('Response is not ok:(');
        }
        let data = await response.json();
        return data;
    } catch (error) {
        console.error('Error while getting word list:', error);
        return [];
    }
}

window.onload = loadQuestions;