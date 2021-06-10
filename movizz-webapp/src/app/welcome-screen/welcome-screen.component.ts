import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-welcome-screen',
  templateUrl: './welcome-screen.component.html',
  styleUrls: ['./welcome-screen.component.css']
})
export class WelcomeScreenComponent implements OnInit {

  title = 'movizz-webapp';
  score = 0;
  highScore = 0;
  timeLeft = 0;
  question = "";

  apiUrl = environment.api;

  constructor(private httpClient: HttpClient) {
    this.httpClient = httpClient;

  }

  ngOnInit(): void {
  }

  onStartQuizz(): void {
    this.getQuestionFromServer();
  }

  onQuizzYesAnswer(): void {
    this.score += 1;
  }

  onQuizzNoAnswer(): void {

  }

  getQuestionFromServer(): void {
    this.httpClient
      .get<string>(this.apiUrl + '/quizz/question')
      .subscribe(
        (response) => {
          this.question = response;
        },
        (error) => {
          console.log('Erreur : ' + error);
        })
  }

}
