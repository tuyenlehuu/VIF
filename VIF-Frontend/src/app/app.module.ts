import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { LocationStrategy, HashLocationStrategy, CommonModule } from '@angular/common';

import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { PERFECT_SCROLLBAR_CONFIG } from 'ngx-perfect-scrollbar';
import { PerfectScrollbarConfigInterface } from 'ngx-perfect-scrollbar';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { AssetService } from './services/asset.service';
import { DashboardService } from './services/dashboard.service';

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true
};

import { AppComponent} from './app.component'

// Import containers
import { DefaultLayoutComponent } from './containers';

import { P403Component } from './views/error/403.component';
import { P404Component } from './views/error/404.component';
import { P500Component } from './views/error/500.component';
import { LoginComponent } from './views/login/login.component';
import { ResetPassComponent } from './views/forgot-pass/forgot.pass.component';
import { ChangeResetPassComponent } from './views/change-reset-pass/change.reset.pass.component';
import { ChangePasswordComponent } from './views/change-password/change.pass.component';
import { AuthGuard } from './guards/auth.guard';
import { OauthService } from './services/oauth.service';
import { UserService } from './services/user.service';
import { JWTInterceptor } from './helpers/jwt.interceptor';
import { ErrorInterceptor } from './helpers/error.interceptor';
import { ReactiveFormsModule } from '@angular/forms';
import { AppTranslationModule } from './app.translation.module';
import { CustomerService } from './services/customer.service';
import { AppParamService } from './services/appParam.service';
import { InvestorTransService } from './services/investor.transaction.service';
import { InvestManagementService } from './services/invest.management.service';
import { NgxCaptchaModule } from 'ngx-captcha';
import { ShareMasterService } from './services/sharemaster.service';
import { BranchService } from './services/branch.service';
import { TransactionService } from './services/transaction.service';
import { HolderequityService } from './services/HolderEquity.service';


const APP_CONTAINERS = [
  DefaultLayoutComponent
];

import {
  AppAsideModule,
  AppBreadcrumbModule,
  AppHeaderModule,
  AppFooterModule,
  AppSidebarModule,
} from '@coreui/angular';

// Import routing module
import { AppRoutingModule } from './app.routing';

// Import 3rd party components
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { InvestRequestService } from './services/invest.request.service';
import { InvestApproService } from './services/investAppro.service';


@NgModule({
  imports: [
    BrowserModule,
    AppRoutingModule,
    AppAsideModule,
    AppBreadcrumbModule.forRoot(),
    AppFooterModule,
    AppHeaderModule,
    AppSidebarModule,
    PerfectScrollbarModule,
    BsDropdownModule.forRoot(),
    TabsModule.forRoot(),
    ChartsModule,
    FormsModule,
    HttpClientModule,
    CommonModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    ReactiveFormsModule,
    AppTranslationModule,
    NgxCaptchaModule
  ],
  declarations: [
    AppComponent,
    ...APP_CONTAINERS,
    P403Component,
    P404Component,
    P500Component,
    LoginComponent,
    ResetPassComponent,
    ChangeResetPassComponent,
    ChangePasswordComponent
  ],
  providers: [{
    provide: LocationStrategy,
    useClass: HashLocationStrategy
  },
  AuthGuard,
  OauthService,
  UserService,
  CustomerService,
  InvestRequestService,
  AppParamService,
  { provide: HTTP_INTERCEPTORS, useClass: JWTInterceptor, multi: true },
  { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  InvestorTransService,
  InvestManagementService,
  AssetService,
  DashboardService,
  ShareMasterService,
  BranchService,
  TransactionService,
  HolderequityService,
  InvestApproService
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
