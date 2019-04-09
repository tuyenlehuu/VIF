import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {UserCustomerComponent} from './customer-users';
import { CustomerManagementComponent } from './customer-management.component';
import { CECustomerComponent } from './customer-management.create.edit';

const routes: Routes = [
  {
    path: '',
    // component: CustomerComponent,
    data: {
      title: 'Quản lý customer' 
    },
    children: [
      {
        path: '',
        redirectTo: 'customer-management'
      },
      {
        path: '',
        component: CustomerManagementComponent,
        data: {
          title: ''
        }
      },
      {
        path: 'ce-customer-management/:id',
        component: CECustomerComponent,
        data: {
          title: '',
          myId: 'myId'
        }
      },
      {
        path: 'user-customer-management/:id',
        component: UserCustomerComponent,
        data: {
          title: '',
          myId: 'hehe'
        }
        
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule {}
