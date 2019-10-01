import { Component, OnDestroy, Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { navItems, navItemsUser } from '../../_nav';
import { OauthService } from '../../services/oauth.service';
import { config } from '../../config/application.config';
import { User } from '../../models/User.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './default-layout.component.html'
})
export class DefaultLayoutComponent implements OnDestroy {
  public username; 
  // public navItems = navItems;
  public navItems;
  public sidebarMinimized = true;
  private changes: MutationObserver;
  public element: HTMLElement;
  constructor(private oauthService:OauthService, @Inject(DOCUMENT) _document?: any) {

    this.changes = new MutationObserver((mutations) => {
      this.sidebarMinimized = _document.body.classList.contains('sidebar-minimized');
    });
    this.element = _document.body;
    this.changes.observe(<Element>this.element, {
      attributes: true,
      attributeFilter: ['class']
    });

    // get user login
    let currentUser = localStorage.getItem(config.currentUser);
    if(currentUser){
      var mUser: User = JSON.parse(currentUser);
      if(mUser){
        this.username = mUser.username;
        // console.log(this.username);
        if(mUser.role === "ROLE_ADMIN"){
          this.navItems = navItems;
        }else{
          this.navItems = navItemsUser;
        }
      }
    }
  }

  ngOnDestroy(): void {
    this.changes.disconnect();
  }

  logout(){
    this.oauthService.logout();
  }
}
