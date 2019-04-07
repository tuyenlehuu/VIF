import { Injectable } from '@angular/core';
import { Http, RequestOptions, Headers, Response } from '@angular/http';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { config } from '../config/application.config';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import { Observable, BehaviorSubject } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { User } from '../models/User.model';

@Injectable({
    providedIn: 'root'
})
export class OauthService {
    private currentUserSubject: BehaviorSubject<User>;
    public currentUser: Observable<User>;
    redirectUrl: string;
    private url = config.apiUrl + '/oauth/token';

    constructor(private router: Router, private http: HttpClient) {
        this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
        this.currentUser = this.currentUserSubject.asObservable();
    }

    public get currentUserValue(): User {
        return this.currentUserSubject.value;
    }

    login(username: string, password: string) {

        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': config.contentType,
                'Authorization': config.requestAuthorization
            })
        };

        let creds = 'username=' + username + '&password=' + password + '&grant_type=password';
        return this.http.post<any>(this.url, creds, httpOptions).pipe(map(token => {
            localStorage.setItem(config.session, JSON.stringify(token));
            if (token && token.access_token) {
                var mUser: User = new User();
                mUser.username = token.username;
                mUser.role = token.authorities[0].authority;
                mUser.token = token.access_token;
                // store user details and jwt token in local storage to keep user logged in between page refreshes
                localStorage.setItem(config.currentUser, JSON.stringify(mUser));
                this.currentUserSubject.next(mUser);
            }
            return mUser;
        }));
    }

    logout() {
        localStorage.removeItem(config.currentUser);
        localStorage.removeItem(config.session);
        this.currentUserSubject.next(null);
        this.router.navigate(['/login']);
    }
}
