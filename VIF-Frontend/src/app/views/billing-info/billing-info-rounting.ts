import { BillingInfoComponent } from './billing-info.component';
import { CEBillingInfoComponent } from './billing-info.component.create.edit';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    // component: CustomerComponent,
    data: {
      title: 'Thông tin thanh toán' 
    },
    children: [
      {
        path: '',
        redirectTo: 'billing-ìno'
      },
      {
        path: '',
        component: BillingInfoComponent,
        data: {
          title: ''
        }
      },
      {
        path: 'ce-billing-info/:id',
        component: CEBillingInfoComponent,
        data: {
          title: '',
          myId: 'myId'
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BillingInfoRoutingModule {}