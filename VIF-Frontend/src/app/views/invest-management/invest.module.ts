// Angular
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { ButtonsModule } from 'ngx-bootstrap/buttons';
import { AppTranslationModule } from '../../app.translation.module';

import {InvestComponent } from './invest.component';

// Invest Routing
import { InvestRoutingModule } from './invest-routing.module';

@NgModule({
  imports: [
    CommonModule,
    InvestRoutingModule,
    FormsModule,
    ModalModule.forRoot(),
    ButtonsModule.forRoot(),
    AppTranslationModule,
  ],
  declarations: [
    InvestComponent
  ]
})
export class InvestModule { }
