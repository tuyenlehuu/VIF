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
import { ShareDividenRoutingModule } from './share-dividend-routing.module';
import { ShareDividendComponent } from './share-dividend.component';

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
    ShareDividenRoutingModule,
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
    ShareDividendComponent
  ]
})
export class ShareDividendModule { }
