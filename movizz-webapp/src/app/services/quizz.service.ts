import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { QuizzQuestion } from "../model/quizz-question";
import {environment} from "../../environments/environment";

@Injectable()
export class QuizzService {

  apiUrl = environment.api;


  constructor(private httpClient: HttpClient) {  }


  getQuestionFromServer(): Observable<any> {
    console.log("Calling API : " + this.apiUrl + "/quizz/question");
    return this.httpClient
      .get<QuizzQuestion>(this.apiUrl + '/quizz/question');
  }

}
