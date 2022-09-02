export function dateFormatYMDHMS(date) {
  var s = new Date(date)
  var y = s.getFullYear()
  var m =
    s.getMonth() + 1 < 10 ? '0' + (s.getMonth() + 1) : s.getMonth() + 1
  var dd = s.getDate() < 10 ? '0' + s.getDate() : s.getDate()
  var hh = s.getHours() < 10 ? '0' + s.getHours() : s.getHours()
  var mm = s.getMinutes() < 10 ? '0' + s.getMinutes() : s.getMinutes()
  var ss = s.getSeconds() < 10 ? '0' + s.getSeconds() : s.getSeconds()
  var enddate = y + '-' + m + '-' + dd + ' ' + hh + ':' + mm + ':' + ss
  return enddate
}

export function dateFormatYMD(date) {
  var s = new Date(date)
  var y = s.getFullYear()
  var m =
    s.getMonth() + 1 < 10 ? '0' + (s.getMonth() + 1) : s.getMonth() + 1
  var dd = s.getDate() < 10 ? '0' + s.getDate() : s.getDate()
  var enddate = y + '-' + m + '-' + dd
  return enddate
}
