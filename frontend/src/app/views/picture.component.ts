import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { dataToImage } from '../utils';
import { UploadService } from '../upload.service';

@Component({
  selector: 'app-picture',
  templateUrl: './picture.component.html',
  styleUrl: './picture.component.css'
})
export class PictureComponent {

  // TODO: Task 2
  // TODO: Task 3

  

  pic!: string
  sub$!: Subscription
  picForm!: FormGroup;

  constructor(private uploadSvc: UploadService, private activatedRoute: ActivatedRoute, private router: Router,  private formBuilder: FormBuilder) {

  }

  ngOnInit(): void {
    this.sub$ = this.activatedRoute.queryParams.subscribe(
      (queryParams: any) => { 
         this.pic = queryParams['pic'] 
        }

    )

    this.picForm = this.formBuilder.group({
      title: ['', [Validators.required, Validators.minLength(5)]],
      comments: [''],
      picture: this.pic,
    });
      }
    
      isFormDirty(){
        return this.picForm.dirty
      }

      postImage(){
        console.log("post")
        const formData = new FormData();
    formData.append('title', this.picForm.get('title')?.value);
      formData.append('comments', this.picForm.get('comments')?.value);
  
    formData.append('picture', dataToImage(this.picForm.get('picture')?.value));
console.log(dataToImage(this.picForm.get('picture')?.value));
    var datestr = (new Date()).toUTCString()
    formData.append('datetime', datestr)

    console.log("postData",formData.get('datetime'))


    this.uploadSvc.upload(formData).subscribe({
      next: (data) => {
        this.picForm.reset();
        this.router.navigate(['/'])

      },
      error: (error) => {
       alert("Error in uploading picture")
      },
      }

    )
      }}
