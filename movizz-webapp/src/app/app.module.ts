import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import {RouterModule, Routes} from "@angular/router";
import { QuizzViewComponent } from './components/quizz-view/quizz-view.component';
import { WelcomeScreenComponent } from './components/welcome-screen/welcome-screen.component';
import {HttpClientModule} from "@angular/common/http";
import {QuizzService} from "./services/quizz.service";
import { GameOverComponent } from './components/game-over/game-over.component';
import { HeaderComponent } from './components/header/header.component';
import { CookieService} from "ngx-cookie-service";

const appRoutes: Routes = [
  { path: 'quizz', component: QuizzViewComponent, children: [{path: '', component: HeaderComponent, outlet: 'routerHeader'}] },
  { path: 'game-over', component: GameOverComponent, children: [{path: '', component: HeaderComponent, outlet: 'routerHeader'}]},
  { path: '', component: WelcomeScreenComponent, children: [{path: '', component: HeaderComponent, outlet: 'routerHeader'}]}
];
@NgModule({

  declarations: [
    AppComponent,
    QuizzViewComponent,
    WelcomeScreenComponent,
    GameOverComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [
    QuizzService,
    CookieService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {


}
