import { NgModule, ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TransactionComponent } from './transaction.component';

const routes: Routes = [
  {

    path: '',
    data:{
      title: 'Lịch sử thay đổi tiền mặt'
    }, children:[
      {
        path:'',
        redirectTo:'transaction-history'
      },
      {
        path:'',
        component:TransactionComponent,
        data:{
          title:''
        }
      }
    ]
    
  }
  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TransactionRouting { }
