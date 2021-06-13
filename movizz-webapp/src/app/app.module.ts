import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {RouterModule, Routes} from "@angular/router";
import { QuizzViewComponent } from './components/quizz-view/quizz-view.component';
import { WelcomeScreenComponent } from './components/welcome-screen/welcome-screen.component';
import {HttpClientModule} from "@angular/common/http";
import {QuizzService} from "./services/quizz.service";

const appRoutes: Routes = [
  { path: 'quizz', component: QuizzViewComponent },
  { path: '', component: WelcomeScreenComponent}
];
@NgModule({

  declarations: [
    AppComponent,
    QuizzViewComponent,
    WelcomeScreenComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [
    QuizzService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {


}
