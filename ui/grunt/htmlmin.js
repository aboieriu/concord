module.exports = {
	min: {
      files: [{
          expand: true,
          cwd: 'src/js/app',
          src: ['*.html', '**/*.html'],
          dest: 'angular/js/app',
          ext: '.html',
          extDot: 'first'
      }]
  }
}