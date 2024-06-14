import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Picture } from "./views/picture";

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  // TODO: Task 3.
  // You may add add parameters to the method
  private baseURL = 'http://127.0.0.1:8080/api/image/upload';

  constructor(private httpClient: HttpClient) {}

  upload(picData: FormData): Observable<any> {
    console.log("service is reached", picData)
    return this.httpClient.post(`${this.baseURL}`, picData);
  }
}
