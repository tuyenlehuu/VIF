import { Injectable } from '@angular/core';
import { Http, RequestOptions, Headers, Response } from '@angular/http';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { config } from '../config/application.config';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class OauthService {
  redirectUrl: string;
  private url = config.apiUrl + '/oauth/token';
  constructor(private router: Router, private http: HttpClient) { }

  login(username: string, password: string){
    
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': config.contentType,
        'Authorization': config.requestAuthorization
      })
    };

    let creds = 'username=' + username + '&password=' + password + '&grant_type=password';
    // return this.http.post(this.url, creds, httpOptions).subscribe(data => {
    //   localStorage.setItem(config.session, JSON.stringify(data));
    // }, (error) => {
    //   console.log('error in', error);
    // });
    return this.http.post<any>(this.url, creds, httpOptions).pipe(map(data=>{
      localStorage.setItem(config.session, JSON.stringify(data));
    }));
    
    // .subscribe(data => {
    //   localStorage.setItem(config.session, JSON.stringify(data));
    // }, (error) => {
    //   console.log('error in', error);
    // });
  }

  logout() {
    // remove os dados da sessão do armazenamento local
    localStorage.removeItem(config.session);
    this.router.navigate(['/login']);
  }
}
