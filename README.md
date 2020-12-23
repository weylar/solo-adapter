# solo-adapter
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![](https://jitpack.io/v/weylar/solo-adapter.svg)](https://jitpack.io/#weylar/solo-adapter)

Solo Adapter is a single view type adapter for recycler view in android.

### Installation
Add to your root build.gradle of your project
```
allprojects {
    repositories {
        ...
        jcenter()
        maven { url 'https://jitpack.io' }
        ...
    }
}
```

### Gradle
```
  implementation 'com.github.weylar:solo-adapter:version'
```
## Usage
Once imported in your project, you can invoke SoloAdapter using either view binding or without view binding:
- Using ViewBinding: You can easily just pass view binding class directly and SoloAdapter knows how to handle the rest. You just need to worry about how to bind your data into your view object.
```
val myAdapter = SoloAdapter.bind(ListItemBinding::inflate) { data, position, payload ->
               this. name.text = data.name
                this.age.text = data.age.toString()
        }
        
myAdapter.adapter.submitList(listOf<>(....))
        )
```
- Without View binding: You just need to pass the layout reference and focus on binding your data on your view object.
```
val myAdapter = SoloAdapter.bind(R.layout.list_item) { data, position, payload ->
            this.findViewById<TextView>(R.id.name).text = data.name
            this.findViewById<TextView>(R.id.age).text = data.age.toString()
            
myAdapter.adapter.submitList(listOf(....))
```
## Parameters
- `data` : This is the data object which contains the information to be binded on your view.
- `position` : Position of the current viewHolder to be binded on.
- `payload` : This is a `MutableList<Any>` data type which contains data passed in when `Adapter.notifyItemChanged(Int, MutableList<Any>)` is called with a payload data.
A simple usage will be like this
```
val adapter = SoloAdapter.bind(ListItemBinding::inflate) { data, position, payload ->
            if (payload.isNullOrEmpty()) {
                name.text = data.name
                age.text = data.age.toString()
            } else {
                age.text = (payload.first() as ArrayList<Student>)[0].age.toString()
            }
        }
        
```
Then you make a call to notify item which has changed like this
```
adapter.notifyItemChanged(2, mutableListOf(MyDataClass()))

```
This can be useful in senerious where you want to be in control of what data is being binded rather than re-rendering your entire viewholder object.
- `this` : Referring to the view object

## Others
You can optionally pass in your own DiffUtil class, by default i.e without passing anyone in. It just compares the entire object.
```
 class MyDiffCallback<MyData> : DiffUtil.ItemCallback<MyData>() {
        override fun areItemsTheSame(oldItem: MyData, newItem: MyData): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: MyData, newItem: MyData): Boolean {
            return oldItem == newItem
        }
    }
    
val myAdapter = SoloAdapter.bind(ListItemBinding::inflate, MyDiffCallback()) { data, position, payload ->
               this. name.text = data.name
                this.age.text = data.age.toString()
        }
        
```
## Using this Library?
You can give me a shout out on [Twitter](https://twitter.com/weylar_) 😉✨

## License
```
  Copyright 2020 Idris Aminu Weylar

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
```
  
