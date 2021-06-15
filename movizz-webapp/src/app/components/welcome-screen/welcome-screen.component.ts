import {Component, OnInit} from '@angular/core';
import {QuizzService} from "../../services/quizz.service";
import {Router} from "@angular/router";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-welcome-screen',
  templateUrl: './welcome-screen.component.html',
  styleUrls: ['./welcome-screen.component.css']
})
export class WelcomeScreenComponent implements OnInit {

  constructor(private quizzService: QuizzService, private router: Router, private cookieService: CookieService) {  }

  ngOnInit(): void {
    this.cookieService.set("score", "0");
  }

  /**
   * Starts the quizz by getting a question and beginning the countdown
   */
  onStartQuizz(): void {
    this.router.navigate(['quizz'])
  }
}
