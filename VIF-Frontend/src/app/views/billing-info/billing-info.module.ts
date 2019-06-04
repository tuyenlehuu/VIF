
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonsModule } from 'ngx-bootstrap/buttons';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxPaginationModule } from 'ngx-pagination';
import { AppTranslationModule } from '../../app.translation.module';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { BillingInfoComponent } from './billing-info.component';
import { CEBillingInfoComponent } from './billing-info.component.create.edit';
import { BillingInfoRoutingModule } from './billing-info-rounting';
import { NgSelectModule } from '@ng-select/ng-select';



@NgModule({
  imports: [
    NgSelectModule,
    FormsModule,
    BillingInfoRoutingModule,
    CommonModule,
    ModalModule.forRoot(),
    ButtonsModule.forRoot(),
    ReactiveFormsModule,
    AppTranslationModule,
    NgxPaginationModule,
    BsDatepickerModule.forRoot(),
    ReactiveFormsModule.withConfig({warnOnNgModelWithFormControl: 'never'})
  ],

  declarations: [BillingInfoComponent, CEBillingInfoComponent]

})
export class BillingInfoModule{ }