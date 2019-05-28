
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonsModule } from 'ngx-bootstrap/buttons';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxPaginationModule } from 'ngx-pagination';
import { AppTranslationModule } from '../../app.translation.module';
import { NgxCurrencyModule } from "ngx-currency";
import { NgSelectModule } from '@ng-select/ng-select';
import {InvestRequestComponent} from './invest-req.component';
import {InvestRequestRouting} from './invest-req-routing';
import { HttpClientModule } from '@angular/common/http';

export const customCurrencyMaskConfig = {
  align: "left",
  allowNegative: false,
  allowZero: true,
  decimal: ".",
  precision: 2,
  prefix: "",
  suffix: "", 
  thousands: ",",
  nullable: false
};

@NgModule({
  imports: [
    HttpClientModule,
    FormsModule,
    InvestRequestRouting,
    CommonModule,
    ModalModule.forRoot(),
    ButtonsModule.forRoot(),
    ReactiveFormsModule,
    AppTranslationModule,
    NgxPaginationModule,
    NgxCurrencyModule.forRoot(customCurrencyMaskConfig),
    NgSelectModule
  ],
  declarations: [InvestRequestComponent]
})
export class InvestRequestModule {
   
}