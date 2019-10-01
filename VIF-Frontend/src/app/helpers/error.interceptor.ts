import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { OauthService } from '../services/oauth.service';
import { Router, ActivatedRoute } from '@angular/router';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor{
    constructor(private oauthService: OauthService, private router: Router){}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError(err => {
            console.log("Error: ", err);
            if (err.status === 401 || err.status === 403) {
                // auto logout if 401 response returned from api
                this.oauthService.logout();
                location.reload(true);
            }
            // console.log("UUU", err.status);

            const error = err.error.message || err.statusText;
            return throwError(error);
        }), 
        map((event: HttpEvent<any>) => {
            if (event instanceof HttpResponse) {
                if(event.body){
                    if(event.body.code && event.body.code === 403){
                        this.router.navigate(['/403']);
                        // console.log('event--->>>', event);
                    }
                }
            }
            return event;
        }))
    }
}