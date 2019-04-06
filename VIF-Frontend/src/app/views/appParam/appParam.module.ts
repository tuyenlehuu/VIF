import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
// import { ChartsModule } from 'ng2-charts/ng2-charts';
// import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ButtonsModule } from 'ngx-bootstrap/buttons';

import { AppParamComponent } from './appParam.component';
import { AppParamRoutingModule } from './appParam-routing.module';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxPaginationModule } from 'ngx-pagination';
import { AppTranslationModule } from '../../app.translation.module';
import { CEAppParamComponent } from './creat.edit.appParam.component';

@NgModule({
  imports: [
    FormsModule,
    AppParamRoutingModule,
    CommonModule,
    ModalModule.forRoot(),
    ButtonsModule.forRoot(),
    ReactiveFormsModule,
    AppTranslationModule,
    NgxPaginationModule
  ],
  declarations: [AppParamComponent, CEAppParamComponent]
})
export class AppParamModule { }
