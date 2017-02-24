### 值班日历

#### 自定义属性列表
| 属性        | 默认值   |  备注  |
| --------   | -----:  | :----:  |
| arrowColor     |0xDD000000 |   切换箭头的颜色值     |
| weeksColor        |   0xDD000000   |   周几的颜色值   |
| weeksTextSize        |    16sp    |  周几的文字大小  |
| titleAnim        |   true   |   标题是否显示动画   |
| titleAnimDuration        |    150    |  标题动画的时长  |

#### 方法
| 方法名        | 用途   | 
| --------   |  -----:  | :----:  |
| setPreListDuty() | 设置上个月的值班日期 |
| setCurrentListDuty() | 设置当前月的值班日期 |
| setNextListDuty() | 设置下个月的值班日期 |
| setArrow() | 设置箭头颜色 |
| setWeeksTextSize() |设置周几的文字大小 |
| setWeeksColor() | 设置周几的文字颜色 |
| setTitleAnim() | 标题是否显示动画 |
| setTitleAnimDuration() | 标题动画的时长 |

#### 2017-2-24
* 修复日期显示多一天的问题
* 适配5.0以下的箭头颜色修改
* 添加自定义属性，周几字段颜色，文字大小属性
* 添加自定义属性，title（显示年月的）是否显示动画，动画的时长

#### 2017-2-23
* 自定义DayView，用于每天的日期View
* 添加自定义属性，左右箭头颜色改变
* 基本功能完善

#### 2017-2-22
* 创建工程，创建日历库modules
* 完成基本框架的搭建
* 使用ViewPager展示日期
* 设定只显示上月，当月，下月的日期

