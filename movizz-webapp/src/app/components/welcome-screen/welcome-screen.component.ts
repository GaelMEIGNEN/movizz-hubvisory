import {Component, OnInit, ViewChild} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {QuizzService} from "../../services/quizz.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-welcome-screen',
  templateUrl: './welcome-screen.component.html',
  styleUrls: ['./welcome-screen.component.css']
})
export class WelcomeScreenComponent implements OnInit {

  constructor(private httpClient: HttpClient, private quizzService: QuizzService, private router: Router) {  }

  ngOnInit(): void {

  }

  onStartQuizz(): void {
    this.quizzService.getQuestionFromServer();

    this.router.navigate(['quizz'])
  }
}
