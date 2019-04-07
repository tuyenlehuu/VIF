import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
// import { ChartsModule } from 'ng2-charts/ng2-charts';
// import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ButtonsModule } from 'ngx-bootstrap/buttons';

import { UserComponent } from './user.component';
import { CEUserComponent } from './create.edit.user.component';

import { UserRoutingModule } from './user-routing.module';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxPaginationModule } from 'ngx-pagination';
import { AppTranslationModule } from '../../app.translation.module';
import { NgSelectModule } from '@ng-select/ng-select';


@NgModule({
  imports:[
    FormsModule,
    UserRoutingModule,
    CommonModule,
    ModalModule.forRoot(),
    ButtonsModule.forRoot(),
    ReactiveFormsModule,
    AppTranslationModule,
    NgxPaginationModule,
    NgSelectModule
  ],
  declarations: [UserComponent, CEUserComponent/*, CustomerManagementComponent*/]
})
export class UserModule { }
