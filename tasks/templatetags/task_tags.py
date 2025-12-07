from django import template

register = template.Library()

@register.filter
def completed_count(tasks):
    return tasks.filter(is_completed=True).count()

@register.filter
def pending_count(tasks):
    return tasks.filter(is_completed=False).count()