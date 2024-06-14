import { Component, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { WebcamComponent, WebcamImage } from 'ngx-webcam';
import { Subject, Subscription } from 'rxjs';
import { CameraService } from '../camera.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent {

  // TODO: Task 1

  @ViewChild(WebcamComponent)
  webcam!: WebcamComponent;

  width =  400;
  height = 400

  pic!: string
  sub$!: Subscription
  trigger = new  Subject<void>;
  constructor(private activatedRoute: ActivatedRoute, private router: Router, private cameraSvc: CameraService){
  }

  ngOnInit(): void {
    this.sub$ = this.activatedRoute.queryParams.subscribe(
      (queryParams: any) => {
        console.info('>>> queryParams: ', queryParams)
        //set default 16:9
        this.height = 282
        if (!!queryParams['height'])
        this.height = parseInt(queryParams['height'])
        console.log('height', queryParams['height'])
      }
    )
  }


  // ngAfterViewInit(): void {
  //     this.webcam.trigger = this.trigger;
  //     this.sub$ = this.webcam.imageCapture.subscribe(
  //       this.snapshot.bind(this)
  //     )
  // }

  snap(){
    this.trigger.next();
    const queryParams = { pic:this.pic }
    this.router.navigate(['/picture'], { queryParams })
  }

  snapshot(webcamImg: WebcamImage){
    // this.cameraSvc.imageData = webcamImg.imageAsDataUrl;
    this.pic = webcamImg.imageAsDataUrl;
  }


  youtube() {

    const queryParams = { height:282 }

    this.router.navigate([ '/' ], { queryParams })

  }


  tiktok() {

    const queryParams = { height:375 }

    this.router.navigate([ '/' ], { queryParams })

  }

  facebook() {

    const queryParams = { height:333 }

    this.router.navigate([ '/' ], { queryParams })

  }

  instagram() {

    const queryParams = { height:500 }

    this.router.navigate([ '/' ], { queryParams })

  }

  ngOnDestroy(): void {

    this.sub$.unsubscribe();
  }

}
