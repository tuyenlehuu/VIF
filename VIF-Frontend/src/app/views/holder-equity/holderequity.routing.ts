import { NgModule, ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HolderequityComponent } from './holderequity.component';
import { CEHolderequityComponent } from './create.edit.holderequity.component';


const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Quản lý vốn cổ đông' 
    },
    children:[
      {
        path:'',
        redirectTo:'holder-equity'
      },
      {
        path:'',
        component:HolderequityComponent,
        data:{
          title:''
        }
      },
      {
        path:'ce-holderequity/:id',
        component:CEHolderequityComponent,
        data:{
          title:''
        }
      },
    ]
    
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HolderequityRouting {}
