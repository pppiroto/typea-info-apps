/*
 *
 */
function trim(str) {
    return str.replace(/^[ 　]+|[ 　]+$/g, "");
}
/*
 *
 */
function getWindowClientHeight() {
	return document.documentElement.clientHeight;
}
/*
 *
 */
function getWindowClientWidth() {
	return document.documentElement.clientWidth;
}
/*
 *
 */
function getInnerHeight() {
  if(document.all) {
     　return document.body.clientHeight;
  } else {
      return window.innerHeight;
  }
}
/*
 *
 */
function getInnerWidth() {
  if(document.all) {
     　return document.body.clientWidth;
  } else {
      return window.innerWidth;
  }
}