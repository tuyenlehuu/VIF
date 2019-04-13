
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonsModule } from 'ngx-bootstrap/buttons';
import { CustomerManagementComponent } from './customer-management.component';
import { CECustomerComponent } from './customer-management.create.edit';
import { CustomerRoutingModule } from './customer-routing.module';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxPaginationModule } from 'ngx-pagination';
import { AppTranslationModule } from '../../app.translation.module';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';

import {UserCustomerComponent} from './customer-users';

//import { CustomerManagementComponent } from './customer-management/customer-management.component';

@NgModule({
  imports: [
    FormsModule,
    CustomerRoutingModule,
    CommonModule,
    ModalModule.forRoot(),
    ButtonsModule.forRoot(),
    ReactiveFormsModule,
    AppTranslationModule,
    NgxPaginationModule,
    BsDatepickerModule.forRoot(),
    ReactiveFormsModule.withConfig({warnOnNgModelWithFormControl: 'never'})
  ],

  declarations: [CustomerManagementComponent, CECustomerComponent, UserCustomerComponent]

})
export class CustomerModule{ }