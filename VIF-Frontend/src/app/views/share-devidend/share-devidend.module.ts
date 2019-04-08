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
import { ShareDevidenRoutingModule } from './share-deviden-routing.module';
import { ShareDevidendComponent } from './share-devidend.component';

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
    ShareDevidenRoutingModule,
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
    ShareDevidendComponent
  ]
})
export class ShareDevidendModule { }
