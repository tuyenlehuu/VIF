import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
// import { ChartsModule } from 'ng2-charts/ng2-charts';
// import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ButtonsModule } from 'ngx-bootstrap/buttons';

import { UserComponent } from './user.component';
import { UserRoutingModule } from './user-routing.module';

@NgModule({
  imports: [
    FormsModule,
    UserRoutingModule,
    ButtonsModule.forRoot()
  ],
  declarations: [ UserComponent ]
})
export class UserModule { }
