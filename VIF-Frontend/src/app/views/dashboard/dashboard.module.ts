import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ChartsModule } from 'ng2-charts';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ButtonsModule } from 'ngx-bootstrap/buttons';
import { TabsModule } from 'ngx-bootstrap/tabs';
import { DashboardComponent } from './dashboard.component';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { GeneralScreenComponent } from './general.screen.component';
import { ReportAssetScreenComponent } from './report.asset.component';
import { NAVScreenComponent } from './nav.vif.component';
import { AppTranslationModule } from '../../app.translation.module';
import { CommonModule } from '@angular/common';
import { NgSelectModule } from '@ng-select/ng-select';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { DebtScreenComponent } from './debt.vif.component';


@NgModule({
  imports: [
    FormsModule,
    DashboardRoutingModule,
    ChartsModule,
    BsDropdownModule,
    ButtonsModule.forRoot(),
    TabsModule,
    AppTranslationModule,
    ReactiveFormsModule,
    CommonModule,
    NgSelectModule,
    BsDatepickerModule.forRoot()
  ],
  declarations: [ 
    DashboardComponent, 
    GeneralScreenComponent, 
    ReportAssetScreenComponent, 
    NAVScreenComponent,
    DebtScreenComponent
  ]
})
export class DashboardModule { }
