import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonsModule } from 'ngx-bootstrap/buttons';

import { StockTrackingComponent } from './stock-tracking.component';
import { CEStockTrackingComponent } from './create-edit-stock-tracking.component';
import { StockTrackingRouting } from './stock-tracking.routing';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxPaginationModule } from 'ngx-pagination';
import { AppTranslationModule } from '../../app.translation.module';
import { NgSelectModule } from '@ng-select/ng-select';
import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { NgxCurrencyModule } from "ngx-currency";

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
    StockTrackingRouting,
    CommonModule,
    ModalModule.forRoot(),
    ButtonsModule.forRoot(),
    ReactiveFormsModule,
    AppTranslationModule,
    NgxPaginationModule,
    NgSelectModule,
    TooltipModule.forRoot(),
    NgxCurrencyModule.forRoot(customCurrencyMaskConfig)
  ],
  declarations: [StockTrackingComponent, CEStockTrackingComponent]
})
export class StockTrackingModule { }
