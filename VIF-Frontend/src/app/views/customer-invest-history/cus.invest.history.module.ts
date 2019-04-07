import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
// import { ChartsModule } from 'ng2-charts/ng2-charts';
// import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ButtonsModule } from 'ngx-bootstrap/buttons';

import { CusInvestHistoryRouting } from './cus-invest.history-routing';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxPaginationModule } from 'ngx-pagination';
import { AppTranslationModule } from '../../app.translation.module';
import { CusInvestHistoryComponent } from './cus-invest.history.component';
import { NgSelectModule } from '@ng-select/ng-select';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';

@NgModule({
  imports: [
    FormsModule,
    CusInvestHistoryRouting,
    CommonModule,
    ModalModule.forRoot(),
    ButtonsModule.forRoot(),
    ReactiveFormsModule,
    AppTranslationModule,
    NgxPaginationModule,
    NgSelectModule,
    BsDatepickerModule.forRoot()
  ],
  declarations: [CusInvestHistoryComponent]
})
export class CusInvestHistoryModule { }
