import {Component, OnInit} from '@angular/core';
import {QuizzService} from "../../services/quizz.service";
import {QuizzQuestion} from "../../model/quizz-question";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-quizz-view',
  templateUrl: './quizz-view.component.html',
  styleUrls: ['./quizz-view.component.css']
})
export class QuizzViewComponent implements OnInit {

  timeLeft: number = 10;
  interval = 0;
  score = 0;
  quizzQuestion: QuizzQuestion = {
    question: "",
    answer: "",
    url_poster: "",
    url_profile: ""
  };

  constructor(private quizzService: QuizzService,private cookieService: CookieService, private router: Router) {
    if (cookieService.get("score") != null) {
      this.score = Number(this.cookieService.get("highScore"));
    }
  }

  ngOnInit(): void {
    this.getQuestionData();
    this.startTimer();
  }

  /**
   * Increases score by 1 if the answer was right
   * Generates a new question from the API
   */
  onQuizzAnswer(answerUser: String): void {
    if (this.cookieService.get("score") != null) {
      this.score = Number(this.cookieService.get("score"));
    }

    if (this.quizzQuestion.answer == answerUser){
      this.score++;
      this.cookieService.set("score", String(this.score));

    }
    this.getQuestionData();
    this.router.navigate( ['quizz']);
  }

  /**
   * Gets a question from the API and puts it inside quizzQuestion
   */
  getQuestionData(): void {
    this.quizzService.getQuestionFromServer().subscribe((result) => {
        this.quizzQuestion = result;
      },
      (error) => {
        console.log('Error : ' + error);
      })
  }

  /**
   * Starts the countdown from 60 to 0
   */
  startTimer() {
    this.interval = window.setInterval(() => {
      if (this.timeLeft > 0){
        this.timeLeft--;
      } else {
        this.pauseTimer();
        this.quizzService.gameOver();
      }
    }, 1000)
  }

  /**
   * Pauses the timer (only used when the quizz is finished
   */
  pauseTimer() {
    window.clearInterval(this.interval);
  }

}
