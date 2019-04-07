import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonsModule } from 'ngx-bootstrap/buttons';
import { InvestorTransComponent } from './investor-trans.component';
import { InvestTransRouting } from './investor-trans-routing';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxPaginationModule } from 'ngx-pagination';
import { AppTranslationModule } from '../../app.translation.module';
import { NgxCurrencyModule } from "ngx-currency";
import { NgSelectModule } from '@ng-select/ng-select';

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
    FormsModule,
    InvestTransRouting,
    CommonModule,
    ModalModule.forRoot(),
    ButtonsModule.forRoot(),
    ReactiveFormsModule,
    AppTranslationModule,
    NgxPaginationModule,
    NgxCurrencyModule.forRoot(customCurrencyMaskConfig),
    NgSelectModule
  ],
  declarations: [InvestorTransComponent]
})
export class InvestorTransModule { }
