import { Component, OnInit } from '@angular/core';
import { OauthService } from '../../services/oauth.service';
import { Router } from '@angular/router';
import { config } from '../../config/application.config';
import { first } from 'rxjs/operators';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
    selector: 'app-dashboard',
    templateUrl: 'login.component.html'
})
export class LoginComponent implements OnInit{
    isLoginFail: boolean = false;
    submitted = false;
    loginForm: FormGroup;

    constructor(private router: Router, private oauthService: OauthService, private fb: FormBuilder) {
    }

    ngOnInit() {
        this.createForm();
    }

    createForm() {
        this.loginForm = this.fb.group({
            username: ['', Validators.required],
            password: ['', Validators.required]
            // username: new FormControl('', [Validators.required, Validators.email]),
        });
    }

    login(username: string, password: string) {
        this.oauthService.login(username, password).pipe(first())
            .subscribe(
                data => {
                    this.router.navigate(['/dashboard']);
                },
                error => {
                    this.router.navigate(['/login']);
                    this.isLoginFail = true;
                }
            );
    }

    get f() { return this.loginForm.controls; }

    onSubmit() {
        this.submitted = true;
        // stop here if form is invalid
        if (this.loginForm.invalid) {
            return;
        }

        this.login(this.loginForm.value.username, this.loginForm.value.password);
    }
}
