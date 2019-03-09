import { NgModule, ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UserComponent } from './user.component';
import { CEUserComponent } from './create.edit.user.component';

const routes: Routes = [
  {
    path: '',
    // component: UserComponent,
    data: {
      title: 'Quản lý user'
    },
    children: [
      {
        path: '',
        redirectTo: 'user-management'
      },
      {
        path: '',
        component: UserComponent,
        data: {
          title: ''
        }
      },
      {
        path: 'ce-user-management/:id',
        component: CEUserComponent,
        data: {
          title: '',
          myId: 'ahihi'
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule {}
