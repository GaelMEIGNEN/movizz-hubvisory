import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'movizz-webapp';
  score = 0;
  highScore = 0;
  timeLeft = 0;
  constructor() { }

  ngOnInit(): void {
  }

  onStartQuizz(): void {

  }

  onQuizzYesAnswer(): void {
    this.score += 1;
  }

  onQuizzNoAnswer(): void {

  }
}
