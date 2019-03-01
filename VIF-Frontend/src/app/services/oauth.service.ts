import { Injectable } from '@angular/core';
import { Http, RequestOptions, Headers, Response } from '@angular/http';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { config } from '../config/application.config';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OauthService {
  isLoggedIn = false;
  redirectUrl: string;
  private url = config.apiUrl + '/oauth/token';
  constructor(private router: Router, private http: HttpClient) { }


  login(username: string, password: string): boolean {
    const headers = new HttpHeaders();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    headers.append('Authorization', config.requestAuthorization);

    // const headers = new Headers();
    // headers.append('Content-Type', 'application/x-www-form-urlencoded');
    // headers.append('Authorization', config.requestAuthorization);
    // const options = new RequestOptions({ headers: headers });
    // this.http.post(this.url, {
    //   username: username,
    //   password: password,
    //   grant_type: 'password'
    // }, options).pipe(map(data => {})).subscribe(result => {
    //   console.log(result);
    // });

    if (username == 'tuyenlh' && password == 'tuyenlh') {
      localStorage.setItem(config.session, "tuyenlh");
      return true;
    }
    return false;
  }

  logout() {
    // remove os dados da sessão do armazenamento local
    localStorage.removeItem(config.session);
    this.router.navigate(['/login']);
  }
}
