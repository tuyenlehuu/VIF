
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonsModule } from 'ngx-bootstrap/buttons';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxPaginationModule } from 'ngx-pagination';
import { AppTranslationModule } from '../../app.translation.module';
import { NgSelectModule } from '@ng-select/ng-select';
import {InvestApproComponent} from './investAppro.component';
import {InvestApproRouting} from './investAppro-routing.module';
import { BsDatepickerModule } from 'ngx-bootstrap';

@NgModule({
  imports: [
    FormsModule,
    InvestApproRouting,
    CommonModule,
    ModalModule.forRoot(),
    ButtonsModule.forRoot(),
    ReactiveFormsModule,
    AppTranslationModule,
    NgxPaginationModule,
    NgSelectModule,
    BsDatepickerModule.forRoot()
  ],
  declarations: [InvestApproComponent]
})
export class InvestApproModule {
   
}