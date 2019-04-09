import { NgModule, ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BranchComponent } from './branch.component';
import { CEBranchComponent } from './create.edit.branch.component';

const routes: Routes = [
  {
    path: '',
    data: {
      title: 'Ngành cổ phiếu' 
    },
    children:[
      {
        path:'',
        redirectTo:'branch-managenment'
      },
      {
        path:'',
        component:BranchComponent,
        data:{
          title:''
        }
      },
      {
        path:'ce-branch-managenment/:id',
        component:CEBranchComponent,
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
export class BranchRouting {}
