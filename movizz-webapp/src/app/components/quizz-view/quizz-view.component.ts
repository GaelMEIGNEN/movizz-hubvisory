import {Component, OnInit} from '@angular/core';
import {QuizzService} from "../../services/quizz.service";
import {QuizzQuestion} from "../../model/quizz-question";

@Component({
  selector: 'app-quizz-view',
  templateUrl: './quizz-view.component.html',
  styleUrls: ['./quizz-view.component.css']
})
export class QuizzViewComponent implements OnInit {

  score = 0;
  quizzQuestion: QuizzQuestion = {
    question: "",
    answer: "",
    url_poster: "",
    url_profile: ""
  };

  constructor(private quizzService: QuizzService) { }

  ngOnInit(): void {
    this.getQuestionData();
  }

  /**
   * Increases score by 1 if the answer was right
   * Generates a new question from the API
   */
  onQuizzAnswer(answerUser: String): void {
    if (this.quizzQuestion.answer == answerUser){
      this.score += 1;
    }
    this.quizzService.getQuestionFromServer();
  }

  /**
   * Gets a question from the API and puts it inside quizzQuestion
   */
  getQuestionData(): void {
    this.quizzService.getQuestionFromServer().subscribe((result) => {
        this.quizzQuestion = result;
        console.log(this.quizzQuestion);
      },
      (error) => {
        console.log('Error : ' + error);
      })
  }

}
