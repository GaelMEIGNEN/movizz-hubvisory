import {Component, OnInit} from '@angular/core';
import {QuizzService} from "../../services/quizz.service";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-game-over',
  templateUrl: './game-over.component.html',
  styleUrls: ['./game-over.component.css']
})
export class GameOverComponent implements OnInit {

  score = 0;
  highScore = 0;

  constructor(private quizzService: QuizzService, private cookieService: CookieService, private router: Router) {
    this.score = Number(this.cookieService.get("score"));
    this.highScore = Number(this.cookieService.get("highScore"));
  }

  ngOnInit(): void {
  }

  /**
   * Starts the quizz by getting a question and beginning the countdown
   */
  onStartQuizz(): void {
    this.quizzService.getQuestionFromServer();
    this.score = Number(this.cookieService.set("score", "0"));
    this.router.navigate(['quizz'])
  }

}
