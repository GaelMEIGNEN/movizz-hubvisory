import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { QuizzQuestion } from "../model/quizz-question";
import {environment} from "../../environments/environment";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";


@Injectable()
export class QuizzService {
  apiUrl = environment.api;


  constructor(private httpClient: HttpClient, private cookieService: CookieService, private router: Router) {  }

  /**
   * Gets a question from our API and creates an object QuizzQuestion with these data
   */
  getQuestionFromServer(): Observable<any> {
    console.log("Calling API : " + this.apiUrl + "/quizz/question");
    return this.httpClient
      .get<QuizzQuestion>(this.apiUrl + '/quizz/question');
  }

  gameOver() {
    this.modifyHighScore();
    this.router.navigate(['game-over']);
  }

  /**
   * Sets the new highScore if score > highScore, and resets the score to 0
   */
  modifyHighScore() {
    var score = Number(this.cookieService.get("score"));
    var highScore = Number(this.cookieService.get("highScore"));
    if (score > highScore) {
      highScore = score;
      this.cookieService.set("highScore", String(highScore));
    }
  }
}
