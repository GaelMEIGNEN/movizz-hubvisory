import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {RouterModule, Routes} from "@angular/router";
import { QuizzViewComponent } from './quizz-view/quizz-view.component';
import { WelcomeScreenComponent } from './welcome-screen/welcome-screen.component';
import {HttpClientModule} from "@angular/common/http";

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
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {


}
