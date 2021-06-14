import { Component, OnInit } from '@angular/core';
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  score = 0;
  highscore = 0;

  constructor(private cookieService: CookieService) {

  }

  ngOnInit(): void {
      this.highscore = Number(this.cookieService.get("highScore"));
      this.score = Number(this.cookieService.get("score"));
  }

}
