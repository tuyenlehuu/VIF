interface NavAttributes {
  [propName: string]: any;
}
interface NavWrapper {
  attributes: NavAttributes;
  element: string;
}
interface NavBadge {
  text: string;
  variant: string;
}
interface NavLabel {
  class?: string;
  variant: string;
}

export interface NavData {
  name?: string;
  url?: string;
  icon?: string;
  badge?: NavBadge;
  title?: boolean;
  children?: NavData[];
  variant?: string;
  attributes?: NavAttributes;
  divider?: boolean;
  class?: string;
  label?: NavLabel;
  wrapper?: NavWrapper;
}

export const navItems: NavData[] = [
  {
    name: 'Tổng quan',
    url: '/dashboard',
    icon: 'icon-speedometer'
  },
  {
    name: 'Quản lý tài sản',
    url: '/theme/colors',
    icon: 'icon-note'
  },
  {
    title: true,
    name: 'Quản lý quỹ'
  },
  {
    name: 'Giao dịch đầu tư',
    url: '/theme/typography',
    icon: 'fa fa-credit-card-alt'
  },
  {
    name: 'Phê duyệt đầu tư',
    url: '/charts',
    icon: 'icon-pencil'
  },
  {
    name: 'Lịch sử quỹ đầu tư',
    url: '/theme/typography',
    icon: 'icon-calendar'
  },
  {
    title: true,
    name: 'Quản lý NĐT'
  },
  {
    name: 'Giao dịch NĐT',
    url: '/investor-transaction',
    icon: 'fa fa-credit-card-alt'
  },
  {
    name: 'Lịch sử NĐT',
    url: '/cus-invest-history',
    icon: 'icon-calendar'
  },
  {
      title: true,
      name: 'Danh mục'
  },
  {
    name: 'Quản lý User',
    url: '/user-management',
    icon: 'icon-user'
  },
  {
    name: 'Quản lý NĐT',
    url: '/theme/colors',
    icon: 'icon-user'
  },
  {
    name: 'Quản lý Phí',
    url: '/theme/typography',
    icon: 'cui-british-pound'
  },
  {
    name: 'Quản lý config',
    url: '/app-param',
    icon: 'cui-british-pound'
  }
];
