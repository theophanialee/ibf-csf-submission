import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { MainComponent } from './views/main.component';
import { PictureComponent } from './views/picture.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import {WebcamModule} from 'ngx-webcam';
import { leavePicComponent } from './guards';
import { UploadService } from './upload.service';

const appRoutes: Routes = [
  { path: '', component: MainComponent },
  { path: 'picture', component: PictureComponent, canDeactivate: [ leavePicComponent ] },
  { path: '**', redirectTo:'/', pathMatch:'full' }
]
@NgModule({
  declarations: [
    AppComponent, MainComponent, PictureComponent
  ],
    imports: [
      BrowserModule,  // Necessary for running the app in a browser
      FormsModule,  // Template-driven forms
      ReactiveFormsModule,  // Reactive forms
      HttpClientModule,  // HTTP communication
      RouterModule.forRoot(appRoutes),  // Configure routes
      WebcamModule
  ],
  providers: [UploadService],
  bootstrap: [AppComponent]
})
export class AppModule { }
