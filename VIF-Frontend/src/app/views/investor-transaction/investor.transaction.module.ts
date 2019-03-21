import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonsModule } from 'ngx-bootstrap/buttons';

// import { UserComponent } from './user.component';
import { InvestorTransComponent } from './investor-trans.component';
import { InvestTransRouting } from './investor-trans-routing';
import { ModalModule } from 'ngx-bootstrap/modal';
import { NgxPaginationModule } from 'ngx-pagination';
import { AppTranslationModule } from '../../app.translation.module';

@NgModule({
  imports: [
    FormsModule,
    InvestTransRouting,
    CommonModule,
    ModalModule.forRoot(),
    ButtonsModule.forRoot(),
    ReactiveFormsModule,
    AppTranslationModule,
    NgxPaginationModule
  ],
  declarations: [InvestorTransComponent]
})
export class InvestorTransModule { }
