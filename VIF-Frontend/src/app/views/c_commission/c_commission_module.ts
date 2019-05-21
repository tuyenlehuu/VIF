// Angular
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ModalModule } from 'ngx-bootstrap/modal';
import { ButtonsModule } from 'ngx-bootstrap/buttons';
import { AppTranslationModule } from '../../app.translation.module';
import { NgxPaginationModule } from 'ngx-pagination';
import { NgxCurrencyModule } from "ngx-currency";
import { NgSelectModule } from '@ng-select/ng-select';

// Invest Routing
import { CompanyCommissionRoutingModule } from './c_commission_routing_module';
import { CompanyCommissionComponent } from './c_commission_component';

export const curencyMaskConfig = {
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
    CommonModule,
    CompanyCommissionRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    AppTranslationModule,
    NgxPaginationModule,
    ModalModule.forRoot(),
    ButtonsModule.forRoot(),
    AppTranslationModule,
    NgxCurrencyModule.forRoot(curencyMaskConfig),
    NgSelectModule
  ],
  declarations: [
    CompanyCommissionComponent
  ]
})
export class CompanyCommissionModule { }
