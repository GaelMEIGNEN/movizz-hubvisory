import {Component, OnInit, ViewChild, } from '@angular/core';
import {QuizzService} from "../../services/quizz.service";
import {QuizzQuestion} from "../../model/quizz-question";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";
import {HeaderComponent} from "../header/header.component";

@Component({
  selector: 'app-quizz-view',
  templateUrl: './quizz-view.component.html',
  styleUrls: ['./quizz-view.component.css']
})
export class QuizzViewComponent implements OnInit {

  @ViewChild(HeaderComponent) header: HeaderComponent;

  timeLeft: number = 60;
  interval = 0;
  score = 0;
  quizzQuestion: QuizzQuestion = {
    question: "",
    answer: "",
    url_poster: "",
    url_profile: ""
  };

  constructor(private quizzService: QuizzService,private cookieService: CookieService, private router: Router) {
    this.header = new HeaderComponent(this.cookieService);
  }

  ngOnInit() {
    this.getQuestionData();
    this.startTimer();
  }

  /**
   * Increases score by 1 if the answer was right
   * Generates a new question from the API
   */
  onQuizzAnswer(answerUser: String): void {

    if (this.quizzQuestion.answer == answerUser){
      this.score++;
      this.header.score = this.score;
      if (this.score > this.header.highscore) {
        this.header.highscore = this.score;
      }
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
        this.quizzService.gameOver(this.header.score);
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
